import apiClient from '@/api/axios.instance';

export interface Kunde {
  id: string;
  name: string;
  mainContact: string;
  city: string;
  contact: string;
  monthlyVolume: string;
  totalColisCount?: number;
}
export const kundeService = {
  async getAll(): Promise<Kunde[]> {
    const response = await apiClient.get<Kunde[]>('/kunden');
    return response.data;
  },

  async getById(id: string): Promise<Kunde> {
    const response = await apiClient.get<Kunde>(`/kunden/${id}`);
    return response.data;
  },

  async create(customer: Omit<Kunde, 'id'>): Promise<Kunde> {
    const response = await apiClient.post<Kunde>('/kunden', customer);
    return response.data;
  },

  async update(id: number, customer: Partial<Kunde>): Promise<Kunde> {
    const response = await apiClient.put<Kunde>(`/kunden/${id}`, customer);
    return response.data;
  },

  async delete(id: number): Promise<void> {
    await apiClient.delete(`/kunden/${id}`);
  },
};